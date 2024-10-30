package com.miller.service.framework.lifecycle;

import com.miller.service.data.entity.AutoCaseRoiLogEntity;
import com.miller.service.data.sql.AutoCaseRoiLogSql;
import com.miller.service.data.sql.AutoCaseRoiSql;
import com.miller.service.framework.annotation.MethodInvoked;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.data.entity.AutoCaseRoiEntity;
import com.miller.service.framework.exception.TestFrameworkException;
import com.miller.service.framework.util.JGitUtils;
import com.miller.service.framework.util.OSUtils;
import com.miller.service.framework.util.ReflectionUtils;
import com.miller.service.util.AutoDBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.extension.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * JUnit 生命周期回调。
 * <p>
 * <pre>
 *         BeforeAllCallback,
 *             BeforeEachCallback,
 *                 // @Test 方法执行之前回调
 *                 BeforeTestExecutionCallback,
 *                     // @Test 执行执行发生异常之后回调
 *                     TestExecutionExceptionHandler,
 *                 // @Test 方法执行之后回调
 *                 AfterTestExecutionCallback,
 *             AfterEachCallback,
 *         AfterAllCallback,
 *         // 下面这三个不太常用，用于测试类创建、处理、销毁时回调
 *         TestInstanceFactory,
 *         TestInstancePostProcessor,
 *         TestInstancePreDestroyCallback
 * </pre>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/18 11:03:13
 */
public class LifecycleCallback implements BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback, TestExecutionExceptionHandler, AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {
    private SqlSession automationSession = AutoDBUtils.getDBOfAutomationTest();
    private AutoCaseRoiSql autoCaseRoiSql = new AutoCaseRoiSql(automationSession);
    private AutoCaseRoiLogSql autoCaseRoiLogSql = new AutoCaseRoiLogSql(automationSession);
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeAll() callback invoked.");
        storeExecutableScenarioValues(extensionContext);
    }


    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterAll() callback invoked.");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeEach() callback invoked.");
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterEach() callback invoked.");
    }

    /**
     * 在 @Test 方法执行之前调用此方法
     */
    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        // @Test方法执行之前调用
        System.out.println(this.getClass().getName() + " beforeTestExecution() callback invoked.");
        MethodInvoked methodInvokedAnnotation = extensionContext.getTestMethod().get().getDeclaredAnnotation(MethodInvoked.class);
        if (Objects.nonNull(methodInvokedAnnotation)) {
            Class<?> clazz = methodInvokedAnnotation.clazz();
            String methodName = methodInvokedAnnotation.methodName();
            String[] args = methodInvokedAnnotation.parameterArgs();
            Class<?>[] parameterTypes = methodInvokedAnnotation.parameterTypes();

            ReflectionUtils.invokeMethod(clazz, methodName, args, parameterTypes);
        }
    }

    /**
     * 在 @Test 方法执行之后调用此方法
     */
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        //@Test方法执行之后调用
        System.out.println(this.getClass().getName() + " afterTestExecution() callback invoked.");
    }

    /**
     * 当 @Test 方法执行时发生异常后会执行此方法.
     * 调用顺序为: @Test -> handleTestExecutionException()
     */
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        System.out.println(this.getClass().getName() + " handleTestExecutionException() callback invoked.");
        // 执行发生异常时把异常抛出来
        throw throwable;
    }

    /**
     * 保存@scenario注解的值
     * @param context
     */
    private void storeExecutableScenarioValues(ExtensionContext context)  {



        //如果测试类本身就有@Scenario注解，则获取注解value
        saveAutoCaseRoi(context.getRequiredTestClass());

        //如果是@Suite集合，则root里获取@Scenario注解,注意只获取一次，子执行类多次root只一次
        ExtensionContext rootContext = context.getRoot();
        String uniqueCls = rootContext.getUniqueId();
        System.out.println("uniqueCls: "+ uniqueCls);
        if(!uniqueCls.contains("suite:")) return;
        String saveUniqueCls = rootContext.getStore(ExtensionContext.Namespace.create(LifecycleCallback.class)).get("uniqueCls",String.class);
        System.out.println("saveUniqueCls: "+ saveUniqueCls);
        if(!uniqueCls.equals(saveUniqueCls)){ //不相等表示首个子类进来，root统计一次
            rootContext.getStore(ExtensionContext.Namespace.create(LifecycleCallback.class)).put("uniqueCls",rootContext.getUniqueId());
            String begin = uniqueCls.substring(uniqueCls.indexOf("suite:")+"suite:".length());
            String suiteCls  = begin.substring(0,begin.indexOf("]"));
            try {
                Class<?> cls = Class.forName(suiteCls); //拿到root父类
                saveAutoCaseRoi(cls);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }

    }
    private String getExecutor(){
        // 获取执行人员
        String executor = "";
        String hostNameOfOS = OSUtils.getHostNameOfOS();
        // 如果是测试环境，则执行人员为DevOps平台
        if (hostNameOfOS.contains("hk-test-")) {
            executor = "DevOps Platform";
        } else {
            // 获取git用户名
            executor = JGitUtils.getGitEmail().split("@")[0];
        }
        return executor;
    }
    private void saveAutoCaseRoi(Class<?> cls){
        Scenario scenario = cls.getDeclaredAnnotation(Scenario.class);
        String executor = getExecutor();
        if(Objects.isNull(scenario)) return;
        if(scenario.developmentTime()<= 0 || scenario.manualTestTime() <= 0 )
            throw new TestFrameworkException(cls.getName() + "developmentTime ,manualTestTime must > 0");
        String scenarioId = scenario.scenarioID();
        AutoCaseRoiEntity autoCaseRoiDB = autoCaseRoiSql.getAutoCaseRoi(scenarioId);
        if(Objects.nonNull(autoCaseRoiDB)){
            autoCaseRoiDB.setScenarioName(scenario.scenarioName());
            autoCaseRoiDB.setDevelopmentTime(scenario.developmentTime());
            autoCaseRoiDB.setMaintenanceTime(scenario.maintenanceTime());
            autoCaseRoiDB.setManualTestTime(scenario.manualTestTime());
            Integer times = autoCaseRoiDB.getTimes()+1;
            Integer saveTimes =autoCaseRoiDB.getSaveTime() +  autoCaseRoiDB.getManualTestTime()   ; //每次执行一次,*1
//            Integer sumCostTimes = autoCaseRoiDB.getDevelopmentTime() + autoCaseRoiDB.getMaintenanceTime();
//            BigDecimal roi = new BigDecimal(saveTimes).divide(new BigDecimal(sumCostTimes),9, RoundingMode.HALF_UP);
            autoCaseRoiDB.setTimes(times); //每执行一次+1
            autoCaseRoiDB.setSaveTime(saveTimes);
            autoCaseRoiDB.setRoi( calculateRoi(autoCaseRoiDB));
            autoCaseRoiDB.setExecutionUser(executor);
            autoCaseRoiSql.updateAutoCaseRoi(autoCaseRoiDB);
            autoCaseRoiLogSql.saveAutoCaseRoiLog(getAutoCaseRoiLog(autoCaseRoiDB));
            System.out.println("autoCaseRoi: "+ autoCaseRoiDB);
        }else {
            AutoCaseRoiEntity autoCaseRoi = new AutoCaseRoiEntity();
            autoCaseRoi.setScenarioId(scenarioId);
            autoCaseRoi.setScenarioName(scenario.scenarioName());
            autoCaseRoi.setDevelopmentTime(scenario.developmentTime());
            autoCaseRoi.setMaintenanceTime(scenario.maintenanceTime());
            autoCaseRoi.setManualTestTime(scenario.manualTestTime());
            autoCaseRoi.setTimes(1);
            autoCaseRoi.setSaveTime(scenario.manualTestTime());
            autoCaseRoi.setRoi(calculateRoi(autoCaseRoi));
            autoCaseRoi.setCreateTime(System.currentTimeMillis());
            autoCaseRoi.setUpdateTime(System.currentTimeMillis());
            autoCaseRoi.setExecutionUser(executor);
            autoCaseRoiSql.saveAutoCaseRoi(autoCaseRoi);
            autoCaseRoiLogSql.saveAutoCaseRoiLog(getAutoCaseRoiLog(autoCaseRoi));
            System.out.println("autoCaseRoi: "+ autoCaseRoi);
        }
    }
    private String calculateRoi(AutoCaseRoiEntity autoCaseRoi){
        Integer saveTimes = autoCaseRoi.getSaveTime();
        Integer sumCostTimes = autoCaseRoi.getDevelopmentTime() + autoCaseRoi.getMaintenanceTime();
        BigDecimal roi = new BigDecimal(saveTimes).divide(new BigDecimal(sumCostTimes),9, RoundingMode.HALF_UP);
        return roi.toString();
    }
    private AutoCaseRoiLogEntity getAutoCaseRoiLog(AutoCaseRoiEntity autoCaseRoi){
        AutoCaseRoiLogEntity autoCaseRoiLog = new AutoCaseRoiLogEntity();
        autoCaseRoiLog.setScenarioId(autoCaseRoi.getScenarioId());
        autoCaseRoiLog.setSaveTime(autoCaseRoi.getSaveTime());
        autoCaseRoiLog.setRoi(autoCaseRoi.getRoi());
        autoCaseRoiLog.setExecutionUser(autoCaseRoi.getExecutionUser());
        autoCaseRoiLog.setCreateTime(System.currentTimeMillis());
        return autoCaseRoiLog;
    }

}
