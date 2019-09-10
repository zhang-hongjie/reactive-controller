package com.example.repository;

import com.example.controller.IntegrationTest;
import com.example.model.MainEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
public class MainRepositoryTest {

    @Autowired
    private MainRepository repository;

    @Test
    public void test_create_update_deleteById() {
        //given
        MainEntity entity = MainEntity.builder().comment("abc").build();
        //when
        MainEntity save = repository.save(entity);
        assertThat(repository.count()).isEqualTo(1);

        save.setDate(new Date());
        MainEntity updated = repository.save(save);

        repository.deleteById(updated.getId());
        assertThat(repository.count()).isEqualTo(0);

        assertThat(repository.findRevisions(updated.getId())).hasSize(3);
    }

    @Test
    public void test_create_update_deleteById_NativeQuery() {
        //given
        MainEntity entity = MainEntity.builder().comment("abc").build();
        //when
        MainEntity save = repository.save(entity);
        assertThat(repository.count()).isEqualTo(1);

        save.setDate(new Date());
        MainEntity updated = repository.save(save);

        repository.deleteByIdNativeQuery(updated.getId());
        assertThat(repository.count()).isEqualTo(0);

        assertThat(repository.findRevisions(updated.getId())).hasSize(2);
    }

    @Test
    public void test_create_nativeQuery() {
        //when
        repository.insertNativeQuery("myId");
        assertThat(repository.count()).isEqualTo(1);
        assertThat(repository.findRevisions("myId")).hasSize(0);
    }
}
