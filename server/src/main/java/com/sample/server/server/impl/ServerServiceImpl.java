package com.sample.server.server.impl;

import com.sample.server.model.Server;
import com.sample.server.repository.ServerRepository;
import com.sample.server.server.ServerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Optional;

import static com.sample.server.common.Status.SERVER_DOWN;
import static com.sample.server.common.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.of;

@Service
@RequiredArgsConstructor
@Transactional
public class ServerServiceImpl implements ServerService {
    private static Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);
    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        logger.info("Saving new server: {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        logger.info("SPinging server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        return serverRepository.save(server);
    }

    @Override
    public List<Server> list(int limit) {
        logger.info("Fetching all servers");
        return serverRepository.findAll(of(  0, limit)).toList();
    }

    @Override
    public Optional<Server> get(Long id) {
        logger.info("Fetching  server by id : {}", id);
        return Optional.of(serverRepository.findById(id).get());
    }

    @Override
    public Server update(Server server) {
        logger.info("updating  server : {}", server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        logger.info("Deleter  server by id : {}", id);
        serverRepository.deleteById(id);
        return TRUE;
    }
}
