package com.miller.userapp.newuserpopup.provider;

import com.miller.userapp.newuserpopup.request.NewUserPopupRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 新人权益弹窗数据-非登陆状态
 */
public class NewUserPopupDataProvider {
    public static Stream<Arguments> setNewUserPopupData(){
        NewUserPopupRequestDTO NewUserPopupRequestdto=new NewUserPopupRequestDTO();
        NewUserPopupRequestdto.setShowModuleAd(0);
        NewUserPopupRequestdto.setShowEntry(0);
        return Stream.of(Arguments.of(NewUserPopupRequestdto));
    }
}
