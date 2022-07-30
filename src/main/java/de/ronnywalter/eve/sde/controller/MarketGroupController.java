package de.ronnywalter.eve.sde.controller;

import com.google.common.collect.Lists;
import de.ronnywalter.eve.dto.MarketGroupDTO;
import de.ronnywalter.eve.sde.model.EveIcon;
import de.ronnywalter.eve.sde.model.InvMarketGroup;
import de.ronnywalter.eve.sde.repository.EveIconRepository;
import de.ronnywalter.eve.sde.repository.MarketGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                dto.setIcon(getIconFileName(iconId));
            }
        });
        return dtos;
    }

    @GetMapping("/{id}")
    public MarketGroupDTO getMarketGroup (@PathVariable int id) {
        MarketGroupDTO dto = map(marketGroupRepository.findById(id).get(), MarketGroupDTO.class);

        Integer iconId = dto.getIconId();
        if(iconId != null) {
            dto.setIcon(getIconFileName(iconId));
        }
        return dto;
    }

    private String getIconFileName(int iconId) {
        EveIcon icon = eveIconRepository.getById(iconId);

        String fileName = icon.getIconFile().substring(icon.getIconFile().lastIndexOf("/") + 1);
        return fileName;
    }
}
