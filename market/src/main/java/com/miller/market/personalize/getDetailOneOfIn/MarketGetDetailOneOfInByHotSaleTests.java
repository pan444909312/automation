package com.miller.market.personalize.getDetailOneOfIn;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.personalize.PersonalizeManageMapper;
import com.miller.market.personalize.getDetailOneOfIn.flow.MarketGetDetailOneOfInFlow;
import com.miller.market.personalize.getDetailOneOfIn.request.MarketGetDetailOneOfInRequestDTO;
import com.miller.market.personalize.getDetailOneOfIn.response.MarketGetDetailOneOfInResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.dal.entity.PersonalizeManage;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 个性化详情接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("个性化详情接口")
public class MarketGetDetailOneOfInByHotSaleTests {
    private static PersonalizeManageMapper personalizeManageMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        personalizeManageMapper = sqlSession.getMapper(PersonalizeManageMapper.class);

    }
    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_获取个性化-热销排行详情接口")
    void getDetailOneOfInByHotSaleSuccessfully(MarketGetDetailOneOfInRequestDTO requestDTO) {
        MarketGetDetailOneOfInResponseDTO responseDTO = MarketGetDetailOneOfInFlow.getDetailOneOfIn(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        MarketGetDetailOneOfInRequestDTO requestDTO = new MarketGetDetailOneOfInRequestDTO();
        //获取自定义专题(启用生效中未删除的自定义专题)
        QueryWrapper<PersonalizeManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("portal_id", BusinessConstant.portalId);
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("effect_status",1);
        queryWrapper.eq("personalize_status",1);
        queryWrapper.eq("personalize_type",1);
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 1");
        PersonalizeManage personalizeManage = personalizeManageMapper.selectOne(queryWrapper);
        requestDTO.setId(personalizeManage.getId());

        Page page = new Page();
        page.setCurrent(1L);
        page.setSize(20L);
        requestDTO.setPage(page);

        return Stream.of(Arguments.of(requestDTO));
    }

}
