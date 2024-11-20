package com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider;

import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/11/20 11:17
 */
public class PandaLeagueDataProvider {
    private static ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO;


    /**
     * 熊猫联盟商卡请求体默认入参
     * @return
     */
    public static ShopListPandaLeagueRequestDTO getCommonDataProvider(){
        if (shopListPandaLeagueRequestDTO == null)
            shopListPandaLeagueRequestDTO = new ShopListPandaLeagueRequestDTO();

        shopListPandaLeagueRequestDTO.setFiltering(false);
        shopListPandaLeagueRequestDTO.setTabType(Byte.parseByte("1"));
        return shopListPandaLeagueRequestDTO;
    }
}
