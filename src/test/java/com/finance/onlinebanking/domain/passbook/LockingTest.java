package com.finance.onlinebanking.domain.passbook;

import com.finance.onlinebanking.config.WithMockCustomUser;
import com.finance.onlinebanking.domain.passbook.dto.TransferRequestDto;
import com.finance.onlinebanking.domain.passbook.entity.DepositWithdrawEntity;
import com.finance.onlinebanking.domain.passbook.repository.DepositWithdrawRepository;
import com.finance.onlinebanking.domain.passbook.service.PassbookService;
import com.finance.onlinebanking.domain.user.entity.UserEntity;
import com.finance.onlinebanking.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("락킹 적용 이체 테스트")
@Nested
@SpringBootTest
class LockingTest {

    @Autowired
    PassbookService passbookService;

    @Autowired
    DepositWithdrawRepository depositWithdrawRepository;

    @Autowired
    UserRepository userRepository;


    @DisplayName("성공")
    @Test
    @WithMockCustomUser
    void success() throws InterruptedException{
        // given
        UserEntity userEntity = UserEntity.builder()
                .username("회원1")
                .build();

        DepositWithdrawEntity passbook1 = DepositWithdrawEntity.builder()
                .accountNumber("123-12-1")
                .password("1234")
                .balance(20000L)
                .interestRate(BigDecimal.valueOf(1))
                .transferLimit(10000L)
                .dtype("DW")
                .build();

        DepositWithdrawEntity passbook2 = DepositWithdrawEntity.builder()
                .accountNumber("345-34-3")
                .password("1234")
                .balance(0L)
                .interestRate(BigDecimal.valueOf(1))
                .transferLimit(10000L)
                .dtype("DW")
                .build();

        userRepository.save(userEntity);
        passbook1.setUser(userEntity);
        passbook2.setUser(userEntity);
        depositWithdrawRepository.save(passbook1);
        depositWithdrawRepository.save(passbook2);

        TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                .amount(1L)
                .memo("테스트 송금")
                .commission(1L)
                .build();

        // when
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 10000;
        ExecutorService service = Executors.newFixedThreadPool(16);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);

        for (int i = 0; i < numberOfExecute; i++) {
            service.execute(() -> {
                try {
                    passbookService.createTransfer(userEntity, passbook1.getId(), passbook2.getId(), transferRequestDto);
                    successCount.getAndIncrement();
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌");
                } catch (Exception e) {
                    System.out.println(e);
                }
                latch.countDown();
            });
        }
        latch.await();

        // then
        assertEquals(10000, successCount.get());
    }
}

