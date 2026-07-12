package com.miller.service.framework.lifecycle;

import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.constant.ExecutionTypeEnum;
import com.miller.entity.constant.PlatformTypeEnum;
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
import com.miller.service.framework.report.sql.UserSql;
import com.miller.service.framework.util.*;
import com.miller.service.framework.report.AutoDBUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.extension.*;
import org.springframework.util.StringUtils;

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
@Slf4j
public class LifecycleCallback implements BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback, TestExecutionExceptionHandler, AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {
    private SqlSession automationSession = AutoDBUtils.getDBOfAutomationTest();
    private AutoCaseRoiSql autoCaseRoiSql = new AutoCaseRoiSql(automationSession);

    private UserSql userSql = new UserSql(automationSession);
    private AutoCaseRoiLogSql autoCaseRoiLogSql = new AutoCaseRoiLogSql(automationSession);
    private AutoExecutionRecordSql autoExecutionRecordSql = new AutoExecutionRecordSql(automationSession);
    private Set<Class<?>> autoTestClasses = new HashSet<>();
    private Map<String, ExecutionStatusEnum> scenarioResultMap = TestResultWatcher.getExecutionStatus();

    /**
     * 调试开关：默认false。
     * 设置为 false 时会自动保存把执行记录保存到数据库中。
     * 设置为 true 则不会保存执行记录到数据库中。
     */
//    private boolean isDebugTestCase = Optional.ofNullable(
//                    new PropertiesUtils().getApplicationPropertiesFileValue("framework.is.debug.testcase"))
//            .map(String::trim)
//            .filter(s -> !s.isEmpty())
//            .map(Boolean::parseBoolean)
//            .orElse(false);
    private boolean isDebugTestCase = false; // 修改开关为false，保存执行记录到数据库中

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        log.info(this.getClass().getName() + " beforeAll() callback invoked.");
        storeExecutableScenarioValues(extensionContext);
    }


    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        log.info(this.getClass().getName() + " afterAll() callback invoked.");
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

                // 0919 更新会有问题，会将最近的一条执行记录更新，影响统计数据，先注释了
//                if (!autoExecutionRecordSuite.getExecutionStatus().equals(value.getCode())) {
//                    autoExecutionRecordSql.updateAutoExecutionRecord(autoExecutionRecordSuite, value);
//                }
            }
        });

    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        log.info(this.getClass().getName() + " beforeEach() callback invoked.");
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        log.info(this.getClass().getName() + " afterEach() callback invoked.");
    }

    /**
     * 在 @Test 方法执行之前调用此方法
     * 目前该@MethodInvoked 注解都无使用，所以该方法仅记录日志使用
     */
    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        // @Test方法执行之前调用
        log.info(this.getClass().getName() + " beforeTestExecution() callback invoked.");
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
        log.info(this.getClass().getName() + " afterTestExecution() callback invoked.");
    }

    /**
     * 当 @Test 方法执行时发生异常后会执行此方法.
     * 调用顺序为: @Test -> handleTestExecutionException()
     */
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        log.info(this.getClass().getName() + " handleTestExecutionException() callback invoked.");
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
        log.info("uniqueCls: " + uniqueCls);
        if (!uniqueCls.contains("suite:")) return;
        String saveUniqueCls = rootContext.getStore(ExtensionContext.Namespace.create(LifecycleCallback.class)).get("uniqueCls", String.class);
//        log.info("saveUniqueCls: " + saveUniqueCls);
        log.debug("saveUniqueCls: " + saveUniqueCls);
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
     * 保存测试用例到数据库
     *
     * @param cls Class
     */
    private void saveAutoCaseRoi(Class<?> cls) {
        Scenario scenario = cls.getDeclaredAnnotation(Scenario.class);
        if (Objects.isNull(scenario)) return;
        // todo 是否可以通过其他方式 让通过平台执行的用例执行人换成其他，免得被识别成定时执行
        // 服务器执行用例作者会是DevOps
        String executor = TestCaseUtils.getExecutor(cls);
        if (!executor.equals(TestCaseUtils.executorOfDevOps)){
            // 修改执行人逻辑，取user表的name
            String author = scenario.author();
            executor = userSql.getUserByUserEmail(author).getName();
        }
        autoTestClasses.add(cls);
        checkScenarioAnnotationValueAreCorrect(cls, scenario);
        String scenarioId = scenario.scenarioID();
        AutoCaseRoiEntity autoCaseRoi = autoCaseRoiSql.getAutoCaseRoi(scenarioId);
        String executionUser = null;
        if (Objects.nonNull(autoCaseRoi)) {
            executionUser = autoCaseRoi.getExecutionUser();
        }


        // ID 不为空表示已经保存过，不需要再保存，则做更新
        if (Objects.nonNull(autoCaseRoi) && !StringUtils.isEmpty(executionUser)) {
            autoCaseRoi.setScenarioName(scenario.scenarioName());
            autoCaseRoi.setDevelopmentTime(scenario.developmentTime());
            autoCaseRoi.setMaintenanceTime(scenario.maintenanceTime());
            autoCaseRoi.setManualTestTime(scenario.manualTestTime());
            autoCaseRoi.setAuthor(scenario.author());
            Integer times = autoCaseRoi.getTimes();
            Long saveTimes = autoCaseRoi.getSaveTime();
            if (!isDebugTestCase) {
                // 每执行一次 + 1
                times = times + 1;
                // 每次执行一次 * 1。手工测试节省时间累计
                saveTimes = autoCaseRoi.getSaveTime() + autoCaseRoi.getManualTestTime();

            }
            autoCaseRoi.setTimes(times);
            autoCaseRoi.setSaveTime(saveTimes);
            autoCaseRoi.setRoi(calculateRoi(autoCaseRoi));
            autoCaseRoi.setExecutionUser(executor);

            // 如果用例的状态是非活跃的，执行后变更为活跃
            if (autoCaseRoi.getStatus() != 0) {
                autoCaseRoi.setStatus(0);
            }

            autoCaseRoiSql.updateAutoCaseRoi(autoCaseRoi);
//            log.info("autoCaseRoi: "+ autoCaseRoi);
        } else {
            // ID 为空表示第一次保存，则做插入
            AutoCaseRoiEntity newAutoCaseRoi = new AutoCaseRoiEntity();
            newAutoCaseRoi.setScenarioId(scenarioId);
            newAutoCaseRoi.setScenarioName(scenario.scenarioName());
            newAutoCaseRoi.setDevelopmentTime(scenario.developmentTime());
            newAutoCaseRoi.setMaintenanceTime(scenario.maintenanceTime());
            newAutoCaseRoi.setManualTestTime(scenario.manualTestTime());
            newAutoCaseRoi.setAuthor(scenario.author());
            // 只在新增用例的时候才写入创建人，默认写本次写入的用例负责人
            newAutoCaseRoi.setCreator(scenario.author());
            newAutoCaseRoi.setTimes(1);
            newAutoCaseRoi.setSaveTime(Long.valueOf(scenario.manualTestTime()));
            newAutoCaseRoi.setRoi(calculateRoi(newAutoCaseRoi));
            newAutoCaseRoi.setCreateTime(System.currentTimeMillis());
            newAutoCaseRoi.setUpdateTime(System.currentTimeMillis());
            newAutoCaseRoi.setExecutionUser(executor);
            newAutoCaseRoi.setPlatformType(PlatformTypeEnum.JAVA.getCode());
            // 处理通过平台新增的scenario_id场景
            if (Objects.nonNull(autoCaseRoi)){
                autoCaseRoiSql.updateAutoCaseRoi(newAutoCaseRoi);
            }else {
                autoCaseRoiSql.saveAutoCaseRoi(newAutoCaseRoi);
            }
//            log.info("autoCaseRoi: "+ autoCaseRoi);
            autoCaseRoi = newAutoCaseRoi;
        }

        // 保存执行记录到测试框架执行记录表，用于记录日志，包含调试和测试记录。
        AutoCaseRoiLogEntity autoCaseRoiLogEntity = getAutoCaseRoiLog(autoCaseRoi);
        autoCaseRoiLogSql.saveAutoCaseRoiLog(autoCaseRoiLogEntity);

        // 保存执行记录到自动化测试执行记录表，用于统计 ROI。不包含调试记录。
        if (!isDebugTestCase) {
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
        if (!scenario.author().toLowerCase().endsWith("@hungrypandagroup.com")) {
            throw new TestFrameworkException(cls.getName() + "author 字段必须为公司邮箱 @hungrypandagroup.com 格式.");
        }
    }

    /**
     * 计算自动化测试用例的 ROI 值
     *
     * @param autoCaseRoi
     * @return 自动化测试用例的 ROI 值
     */
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

    /**
     * 自动化测试执行记录
     *
     * @param autoCaseRoiLog 执行记录
     * @param executor       执行人
     * @return AutoExecutionRecordEntity
     */
    private AutoExecutionRecordEntity getAutoExecutionRecord(AutoCaseRoiLogEntity autoCaseRoiLog, String executor) {
        AutoExecutionRecordEntity autoExecutionRecord = new AutoExecutionRecordEntity();
        autoExecutionRecord.setScenarioId(autoCaseRoiLog.getScenarioId());
        autoExecutionRecord.setDevelopmentTime(autoCaseRoiLog.getDevelopmentTime());
        autoExecutionRecord.setManualTestTime(autoCaseRoiLog.getManualTestTime());
        autoExecutionRecord.setMaintenanceTime(autoCaseRoiLog.getMaintenanceTime());

        // 执行策略，区分是人工提效，还是机器定时执行
        if (executor.equals(TestCaseUtils.executorOfDevOps)) {
            // 机器执行
            autoExecutionRecord.setExecutionType(ExecutionTypeEnum.DAILY_CHECK.getCode());
        } else {
            // 人工执行
            autoExecutionRecord.setExecutionType(ExecutionTypeEnum.EFFICIENCY_IMPROVEMENT.getCode());
        }
        // 如果执行人获取不到则无法判断是人工执行还是机器执行
        if (executor.isBlank()) {
            autoExecutionRecord.setExecutionType(ExecutionTypeEnum.UNKNOWN_STRATEGY.getCode());
        }
        autoExecutionRecord.setExecutionUser(executor);
        autoExecutionRecord.setCreateTime(System.currentTimeMillis());
        autoExecutionRecord.setExecutionTime(System.currentTimeMillis());
        // 默认先存-1，此时还获取不到执行状态
        autoExecutionRecord.setExecutionStatus(ExecutionStatusEnum.ERROR.getCode());
        autoExecutionRecord.setUpdateTime(System.currentTimeMillis());
        return autoExecutionRecord;
    }

}
