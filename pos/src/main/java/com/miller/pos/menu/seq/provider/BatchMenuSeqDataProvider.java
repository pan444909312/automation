package com.miller.pos.menu.seq.provider;

import com.miller.pos.menu.edit.request.EditMenuRequestDTO;
import com.miller.pos.menu.seq.request.BatchMenuSeqRequestDTO;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.ResourceUtils;
import com.panda.pos.server.api.dto.open.NameMultiLanguageDTO;
import com.panda.pos.server.api.dto.open.menu.MenuSeqRequest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class BatchMenuSeqDataProvider {


    /**
     * 菜单 - 批量修改菜单排序接口 - 初始化数据
     *
     * @return
     */
    static Stream<Arguments> batchMenuSeqDataProvider() {

        int seq = new Random().nextInt(100);
        List<MenuSeqRequest.MenuSeq> menuSeqList = new LinkedList<>();


        MenuSeqRequest.MenuSeq menuSeq = new MenuSeqRequest.MenuSeq();
        menuSeq.setSeq(seq);
        menuSeq.setId(4166810L);
        menuSeqList.add(menuSeq);


        BatchMenuSeqRequestDTO requestDTO = new BatchMenuSeqRequestDTO();
        requestDTO.setMenuSeqList(menuSeqList);

        return Stream.of(
                Arguments.arguments(requestDTO)
        );


    }

}
