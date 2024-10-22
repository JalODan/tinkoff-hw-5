package kz.oj.tinkoffhw5.integration.kudago;

import kz.oj.tinkoffhw5.entity.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "locations", url = "https://kudago.com/public-api/v1.4/locations")
public interface LocationClient {

    @GetMapping
    List<Location> findAll();
}
