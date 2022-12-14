package com.nuban.accountclassification.controller;


import com.nuban.accountclassification.dto.BankDataDto;
import com.nuban.accountclassification.dto.CustomApiResponseDto;
import com.nuban.accountclassification.enumerations.SuccessMessage;
import com.nuban.accountclassification.service.BankAccountClassifierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Author : Todimu Isewon
 * Date : 04/09/2022
 */

@RestController
@RequestMapping("api/v1")
public class BankAccountValidationController {

    private final Logger log = LoggerFactory.getLogger(BankAccountValidationController.class);

    @Autowired
    private BankAccountClassifierService bankAccountClassifierService;

    @GetMapping("/bank-list/{accountNumber}")
    public CustomApiResponseDto getListOfPossibleBanks(@PathVariable String accountNumber) throws IOException {
        log.debug("REST request to get referral data : {}", accountNumber);

        List<BankDataDto> listOfPossibleBanks = bankAccountClassifierService.getListOfPossibleBanks(accountNumber);

        if (listOfPossibleBanks.isEmpty()) {
            throw new RuntimeException("Bank account does not belong to any bank");
        }

        CustomApiResponseDto customApiResponseDto = CustomApiResponseDto
                .builder()
                .code(SuccessMessage.SUCCESS_MESSAGE.getCode())
                .message(SuccessMessage.SUCCESS_MESSAGE.getMessage())
                .data(listOfPossibleBanks)
                .build();

        return customApiResponseDto;
    }

}
