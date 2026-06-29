package com.example.scp.test.mockito;

import com.example.scp.component.BonbonComponent;
import com.example.sibling.SiblingBonbonComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BonbonComponentMockitoTest {

    @Mock
    private SiblingBonbonComponent siblingBonbonComponent;
    @Spy
    @InjectMocks
    private BonbonComponent bonbonComponent;

    @Test
    public void test() {
        Assertions.assertEquals("Houston", bonbonComponent.comCheck());
        verify(siblingBonbonComponent, times(1)).run();
        verify(bonbonComponent, times(1)).comCheck();
    }

}
