package fyi.dslab.car.maintenance.tracker.users.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@RedisHash("UserAuthCode")
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthCode implements Serializable {

    @Id
    private String email;
    private String authCode;
    @TimeToLive
    private Long expirationInSeconds;

}
