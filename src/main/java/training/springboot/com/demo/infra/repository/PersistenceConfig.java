//package training.springboot.com.demo.infra.repository;
//
//import org.h2.tools.Server;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.sql.SQLException;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "training.springboot.com.demo.domain")
//@EntityScan(basePackages = {"training.springboot.com.demo.domain"})
//@EnableTransactionManagement
//public class PersistenceConfig {
//
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    @Profile("acceptance-local")
//    public Server inMemoryH2DatabaseaServer() throws SQLException {
//        return Server.createTcpServer(
//                "-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
//    }
//
//}
