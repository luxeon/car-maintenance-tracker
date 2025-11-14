package fyi.dslab.car.maintenance.tracker;

import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.lang.annotation.*;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJdbc
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(executionPhase = AFTER_TEST_CLASS, value = "/db/clean.sql")
public @interface IntegrationTest {
}
