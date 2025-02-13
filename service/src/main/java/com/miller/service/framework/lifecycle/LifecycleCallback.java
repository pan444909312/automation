package com.miller.service.framework.lifecycle;

import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.report.AutoExecutionRecordEntity;
import com.miller.service.framework.report.entity.AutoCaseRoiLogEntity;
import com.miller.service.framework.report.sql.AutoCaseRoiLogSql;
import com.miller.service.framework.report.sql.AutoCaseRoiSql;
import com.miller.service.framework.report.sql.AutoExecutionRecordSql;
import com.miller.service.framework.annotation.MethodInvoked;
import com.miller.service.framework.annotation.Scenario;
import com.miller.entity.report.AutoCaseRoiEntity;
import com.miller.service.framework.exception.TestFrameworkException;
import com.miller.service.framework.listenner.TestResultWatcher;
import com.miller.service.framework.util.*;
import com.miller.service.framework.report.AutoDBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.extension.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
    private AutoExecutionRecordSql autoExecutionRecordSql = new AutoExecutionRecordSql(automationSession);
    private Set<Class<?>> autoTestClasses = new HashSet<>();
    private Map<String, ExecutionStatusEnum> scenarioResultMap = TestResultWatcher.getExecutionStatus();

    /**
     * 是否保存自动化测试执行记录到数据库表中的开关。
     * 调试时设置为 false， 设置为 true 则会把执行记录保存到数据库中，会影响测试用例的 ROI 统计。
     */
    private boolean isNotSaveAutomationExecutionRecord = Boolean.parseBoolean(
            new PropertiesUtils().getApplicationPropertiesFileValue("framework.is.not.save.automation.execution.record"));

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeAll() callback invoked.");
        storeExecutableScenarioValues(extensionContext);
    }


    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterAll() callback invoked.");
        // 遍历所有测试类，更新测试类执行结果到数据库
        autoTestClasses.stream().forEach(cls -> {
            Scenario scenario = cls.getDeclaredAnnotation(Scenario.class);
            String scenarioId = scenario.scenarioID();
            ExecutionStatusEnum value = scenarioResultMap.get(scenarioId);
            AutoExecutionRecordEntity autoExecutionRecord = autoExecutionRecordSql.getAutoExecutionRecord(scenarioId);
            AutoExecutionRecordEntity autoExecutionRecordSuite = autoExecutionRecordSql.getAutoExecutionRecordBySuite(scenarioId);
            // 更新 TestCase 执行结果
            if (Objects.nonNull(autoExecutionRecord)) {
                autoExecutionRecordSql.updateAutoExecutionRecord(autoExecutionRecord, value);
            }
            //更新 TestSuite 执行结果。suite会多次（子类多个），所以如果不是失败的需要再次更新（map已校验） && !autoExecutionRecordSuite.getExecutionStatus().equals(ExecutionStatusEnum.FAIL.getCode())
            if (Objects.nonNull(autoExecutionRecordSuite)) {
                // 测试执行结果与数据库最新的一次执行记录不等才做更新操作，减少 update 操作
                if (!autoExecutionRecordSuite.getExecutionStatus().equals(value.getCode())) {
                    autoExecutionRecordSql.updateAutoExecutionRecord(autoExecutionRecordSuite, value);
                }
            }
        });

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
     *
     * @param context ExtensionContext
     */
    private void storeExecutableScenarioValues(ExtensionContext context) {
        //如果测试类本身就有@Scenario注解，则获取注解value
        saveAutoCaseRoi(context.getRequiredTestClass());
        //如果是@Suite集合，则root里获取@Scenario注解,注意只获取一次，子执行类多次root只一次
        ExtensionContext rootContext = context.getRoot();
        String uniqueCls = rootContext.getUniqueId();
        System.out.println("uniqueCls: " + uniqueCls);
        if (!uniqueCls.contains("suite:")) return;
        String saveUniqueCls = rootContext.getStore(ExtensionContext.Namespace.create(LifecycleCallback.class)).get("uniqueCls", String.class);
        System.out.println("saveUniqueCls: " + saveUniqueCls);
        if (!uniqueCls.equals(saveUniqueCls)) { //不相等表示首个子类进来，root统计一次
            rootContext.getStore(ExtensionContext.Namespace.create(LifecycleCallback.class)).put("uniqueCls", rootContext.getUniqueId());
            String begin = uniqueCls.substring(uniqueCls.indexOf("suite:") + "suite:".length());
            String suiteCls = begin.substring(0, begin.indexOf("]"));
            try {
                Class<?> cls = Class.forName(suiteCls); //拿到root父类
                saveAutoCaseRoi(cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取执行人员
     *
     * @return 执行人员
     */
    private String getExecutor() {
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

    /**
     * 保存测试用例到数据库
     *
     * @param cls Class
     */
    private void saveAutoCaseRoi(Class<?> cls) {
        Scenario scenario = cls.getDeclaredAnnotation(Scenario.class);
        String executor = getExecutor();
        if (Objects.isNull(scenario)) return;
        autoTestClasses.add(cls);
        checkScenarioAnnotationValueAreCorrect(cls, scenario);
        String scenarioId = scenario.scenarioID();
        AutoCaseRoiEntity autoCaseRoi = autoCaseRoiSql.getAutoCaseRoi(scenarioId);
        // ID 不为空表示已经保存过，不需要再保存，则做更新
        if (Objects.nonNull(autoCaseRoi)) {
            autoCaseRoi.setScenarioName(scenario.scenarioName());
            autoCaseRoi.setDevelopmentTime(scenario.developmentTime());
            autoCaseRoi.setMaintenanceTime(scenario.maintenanceTime());
            autoCaseRoi.setManualTestTime(scenario.manualTestTime());
            Integer times = autoCaseRoi.getTimes() + 1;
            Long saveTimes = autoCaseRoi.getSaveTime() + autoCaseRoi.getManualTestTime(); //每次执行一次,*1
//            Integer sumCostTimes = autoCaseRoiDB.getDevelopmentTime() + autoCaseRoiDB.getMaintenanceTime();
//            BigDecimal roi = new BigDecimal(saveTimes).divide(new BigDecimal(sumCostTimes),9, RoundingMode.HALF_UP);
            autoCaseRoi.setTimes(times); //每执行一次+1
            autoCaseRoi.setSaveTime(saveTimes);
            autoCaseRoi.setRoi(calculateRoi(autoCaseRoi));
            autoCaseRoi.setExecutionUser(executor);
            autoCaseRoiSql.updateAutoCaseRoi(autoCaseRoi);
//            System.out.println("autoCaseRoi: "+ autoCaseRoi);
        } else {
            // ID 为空表示第一次保存，则做插入
            autoCaseRoi = new AutoCaseRoiEntity();
            autoCaseRoi.setScenarioId(scenarioId);
            autoCaseRoi.setScenarioName(scenario.scenarioName());
            autoCaseRoi.setDevelopmentTime(scenario.developmentTime());
            autoCaseRoi.setMaintenanceTime(scenario.maintenanceTime());
            autoCaseRoi.setManualTestTime(scenario.manualTestTime());
            autoCaseRoi.setTimes(1);
            autoCaseRoi.setSaveTime(Long.valueOf(scenario.manualTestTime()));
            autoCaseRoi.setRoi(calculateRoi(autoCaseRoi));
            autoCaseRoi.setCreateTime(System.currentTimeMillis());
            autoCaseRoi.setUpdateTime(System.currentTimeMillis());
            autoCaseRoi.setExecutionUser(executor);
            autoCaseRoiSql.saveAutoCaseRoi(autoCaseRoi);
//            System.out.println("autoCaseRoi: "+ autoCaseRoi);
        }

        // 保存执行记录到测试框架执行记录表，用于记录日志
        AutoCaseRoiLogEntity autoCaseRoiLogEntity = getAutoCaseRoiLog(autoCaseRoi);
        autoCaseRoiLogSql.saveAutoCaseRoiLog(autoCaseRoiLogEntity);

        // 保存执行记录到自动化测试执行记录表，用于统计 ROI
        if (!isNotSaveAutomationExecutionRecord) {
            AutoExecutionRecordEntity autoExecutionRecord = getAutoExecutionRecord(autoCaseRoiLogEntity, executor);
            autoExecutionRecordSql.saveAutoExecutionRecord(autoExecutionRecord);
        }
    }

    /**
     * 校验场景注解值是否正确
     *
     * @param cls
     * @param scenario
     */
    private static void checkScenarioAnnotationValueAreCorrect(Class<?> cls, Scenario scenario) {
        if (scenario.developmentTime() <= 0 || scenario.manualTestTime() <= 0)
            throw new TestFrameworkException(cls.getName() + "developmentTime ,manualTestTime must > 0");
        if (scenario.author().toLowerCase().endsWith("@hungrypandagroup.com")) {
            throw new TestFrameworkException(cls.getName() + "author 字段必须为公司邮箱 @hungrypandagroup.com 格式.");
        }
    }

    private String calculateRoi(AutoCaseRoiEntity autoCaseRoi) {
        Long saveTimes = autoCaseRoi.getSaveTime();
        Integer sumCostTimes = autoCaseRoi.getDevelopmentTime() + autoCaseRoi.getMaintenanceTime();
        BigDecimal roi = new BigDecimal(saveTimes).divide(new BigDecimal(sumCostTimes), 9, RoundingMode.HALF_UP);
        return roi.toString();
    }

    private AutoCaseRoiLogEntity getAutoCaseRoiLog(AutoCaseRoiEntity autoCaseRoi) {
        AutoCaseRoiLogEntity autoCaseRoiLog = new AutoCaseRoiLogEntity();
        autoCaseRoiLog.setScenarioId(autoCaseRoi.getScenarioId());
        autoCaseRoiLog.setSaveTime(autoCaseRoi.getSaveTime());
        autoCaseRoiLog.setRoi(autoCaseRoi.getRoi());
        autoCaseRoiLog.setDevelopmentTime(autoCaseRoi.getDevelopmentTime());
        autoCaseRoiLog.setManualTestTime(autoCaseRoi.getManualTestTime());
        autoCaseRoiLog.setMaintenanceTime(autoCaseRoi.getMaintenanceTime());
        autoCaseRoiLog.setExecutionUser(autoCaseRoi.getExecutionUser());
        autoCaseRoiLog.setCreateTime(System.currentTimeMillis());
        return autoCaseRoiLog;
    }

    private AutoExecutionRecordEntity getAutoExecutionRecord(AutoCaseRoiLogEntity autoCaseRoiLog, String executor) {
        AutoExecutionRecordEntity autoExecutionRecord = new AutoExecutionRecordEntity();
        autoExecutionRecord.setScenarioId(autoCaseRoiLog.getScenarioId());
        autoExecutionRecord.setDevelopmentTime(autoCaseRoiLog.getDevelopmentTime());
        autoExecutionRecord.setManualTestTime(autoCaseRoiLog.getManualTestTime());
        autoExecutionRecord.setMaintenanceTime(autoCaseRoiLog.getMaintenanceTime());
        if ("".equals(executor)) {
            autoExecutionRecord.setExecutionType(ExecutionTypeEnum.UNKNOWN_STRATEGY.getCode());
        } else if ("DevOps Platform".equals(executor)) {
            autoExecutionRecord.setExecutionType(ExecutionTypeEnum.DAILY_CHECK.getCode());
        } else {
            autoExecutionRecord.setExecutionType(ExecutionTypeEnum.QUALITY_ASSURANCE.getCode());
        }
        autoExecutionRecord.setExecutionUser(executor);
        autoExecutionRecord.setCreateTime(System.currentTimeMillis());
        autoExecutionRecord.setExecutionTime(System.currentTimeMillis());
        autoExecutionRecord.setExecutionStatus(ExecutionStatusEnum.ERROR.getCode());
        autoExecutionRecord.setUpdateTime(System.currentTimeMillis());
        return autoExecutionRecord;
    }

}
