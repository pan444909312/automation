package com.miller.bdm.app.privateShop.formula.provider;

import com.miller.bdm.app.privateShop.formula.request.GetFormulaListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
@SuppressWarnings(value = "unused")
public class GetFormulaListDataProvider {
    static Stream<Arguments> GetTemplatesList() {
        GetFormulaListRequestDTO GetTemplatesRequestDTO = new GetFormulaListRequestDTO();
        return Stream.of(
                arguments(GetTemplatesRequestDTO)
        );
    }
}
