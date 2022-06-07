package de.ronnywalter.eve.sde.controller;

import com.google.common.collect.Lists;
import de.ronnywalter.eve.dto.MarketGroupDTO;
import de.ronnywalter.eve.sde.model.EveIcon;
import de.ronnywalter.eve.sde.repository.EveIconRepository;
import de.ronnywalter.eve.sde.repository.MarketGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("marketgroups")
@RequiredArgsConstructor
public class MarketGroupController extends AbstractController{

    private final MarketGroupRepository marketGroupRepository;
    private final EveIconRepository eveIconRepository;

    @GetMapping("")
    public List<MarketGroupDTO> getMarketGroups () {
        List<MarketGroupDTO> dtos = mapList(Lists.newArrayList(marketGroupRepository.findAll()), MarketGroupDTO.class);

        dtos.forEach(dto -> {
            Integer iconId = dto.getIconId();
            if(iconId != null) {
                EveIcon icon = eveIconRepository.getById(dto.getIconId());

                String fileName = icon.getIconFile().substring(icon.getIconFile().lastIndexOf("/") + 1);
                dto.setIcon(fileName);

            }
        });

        return dtos;
    }

}
