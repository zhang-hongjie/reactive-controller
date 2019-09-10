package com.example.repository;

import com.example.controller.IntegrationTest;
import com.example.model.SqlDeleteEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
public class SqlDeleteRepositoryTest {

    @Autowired
    private SqlDeleteRepository repository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void test_create_update_delete_SQLDelete() {
        //given
        SqlDeleteEntity entity = SqlDeleteEntity.builder().comment("abc").build();
        //when
        SqlDeleteEntity save = repository.save(entity);
        assertThat(repository.count()).isEqualTo(1);

        save.setDate(new Date());
        SqlDeleteEntity updated = repository.save(save);

        repository.delete(updated);
        assertThat(repository.count()).isEqualTo(0);

        assertThat(repository.findRevisions(updated.getId())).hasSize(3);
    }

    @Test
    public void test_create_update_deleteById_SQLDelete() {
        //given
        SqlDeleteEntity entity = SqlDeleteEntity.builder().comment("abc").build();
        //when
        SqlDeleteEntity save = repository.save(entity);
        assertThat(repository.count()).isEqualTo(1);

        save.setDate(new Date());
        SqlDeleteEntity updated = repository.save(save);

        repository.deleteById(updated.getId());
        assertThat(repository.count()).isEqualTo(0);

        assertThat(repository.findRevisions(updated.getId())).hasSize(3);
    }

    @Test
    @Transactional
    public void test_create_update_emRemove() {
        //given
        SqlDeleteEntity entity = SqlDeleteEntity.builder().comment("abc").build();
        //when
        SqlDeleteEntity save = repository.save(entity);
        assertThat(repository.count()).isEqualTo(1);

        save.setDate(new Date());
        SqlDeleteEntity updated = repository.save(save);

//        em.remove(updated);
//        assertThat(repository.count()).isEqualTo(1);

        assertThat(repository.findRevisions(updated.getId())).hasSize(3);
    }
}
