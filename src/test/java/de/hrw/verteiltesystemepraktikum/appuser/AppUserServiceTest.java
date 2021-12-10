package de.hrw.verteiltesystemepraktikum.appuser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {

    @Mock
    AppUserRepository mockRepository;

    @Mock
    AppUserService mockAppUserService;

    @Test
    public void countAppUserTest() {
        Mockito.when(mockRepository.count()).thenReturn(123L);
        long appUserCount = mockRepository.count();

        Assert.assertEquals(123L, appUserCount);
        Mockito.verify(mockRepository).count();
    }

    @Test
    public void deleteUserByIdTest() {
        Mockito.when(mockRepository.existsById(anyLong())).thenReturn(false);
        mockAppUserService.deleteUserById(anyLong());
        Mockito.verifyNoInteractions(mockRepository);
    }
}
