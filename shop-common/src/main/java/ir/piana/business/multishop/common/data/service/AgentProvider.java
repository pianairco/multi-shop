package ir.piana.business.multishop.common.data.service;

import ir.piana.business.multishop.common.data.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("agentProvider")
public class AgentProvider {
    @Autowired
    private AgentRepository agentRepository;

    /*@Transactional
    public AgentEntity createAgentEntity(String uuid) {
        AgentEntity agent = AgentEntity.builder().username(uuid).build();
        agentRepository.save(agent);
        return agent;
    }*/
}
