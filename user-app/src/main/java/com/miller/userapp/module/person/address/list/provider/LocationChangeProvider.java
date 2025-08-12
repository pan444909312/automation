package com.miller.userapp.module.person.address.list.provider;

import com.miller.service.framework.util.JSONUtils;
import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import com.miller.userapp.module.person.address.list.request.LocationChangeRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/5/31 16:17
 */
@SuppressWarnings("unused")
public class LocationChangeProvider {
    static Stream<Arguments> locationChangeData() {

        String str = "{\"currentLongitude\":120.2165044,\"lastLongitude\":120.683708,\"currentLatitude\":30.20905210000000512,\"lastLatitude\":28.01104500000000512}";

        AddressRequestDTO addressRequestDTO = JSONUtils.jsonToObject(str, AddressRequestDTO.class);
        LocationChangeRequestDTO locationChangeRequestDTO = JSONUtils.jsonToObject(str,LocationChangeRequestDTO.class);

        return Stream.of(
                Arguments.of(locationChangeRequestDTO));
    }
}
