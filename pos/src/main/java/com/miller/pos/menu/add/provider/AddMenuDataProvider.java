package com.miller.pos.menu.add.provider;

import com.miller.pos.menu.add.request.AddMenuRequestDTO;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.ResourceUtils;
import com.panda.pos.server.api.dto.open.NameMultiLanguageDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class AddMenuDataProvider {


    /**
     * 菜单 - 创建接口 - 初始化数据
     *
     * @return
     */
    static Stream<Arguments> addMenuDataProvider() {

        // 通过json文件初始化数据
        final String requestJson = ResourceUtils.readTestCaseDataFromResourcesPath("addMenu.json");
        AddMenuRequestDTO dto = JSONUtils.jsonToObject(requestJson, AddMenuRequestDTO.class);

        // 设置动态参数 中文名称和英文名称
        NameMultiLanguageDTO nameDto = new NameMultiLanguageDTO();
        final long time = System.currentTimeMillis();
        String menuNameZhCn = "Pos_菜单：" + time;
        String menuNameEnUs = "Pos_Menu：" + time;
        nameDto.setZhCn(menuNameZhCn);
        nameDto.setEnUs(menuNameEnUs);
        dto.setName(nameDto);


        return Stream.of(
                Arguments.arguments(dto)
        );


    }

}
