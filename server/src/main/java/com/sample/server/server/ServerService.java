package com.sample.server.server;

import com.sample.server.model.Server;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    List<Server> list(int limit);
    Optional<Server> get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}
