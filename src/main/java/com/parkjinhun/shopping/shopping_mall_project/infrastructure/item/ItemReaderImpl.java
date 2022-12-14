package com.parkjinhun.shopping.shopping_mall_project.infrastructure.item;

import com.parkjinhun.shopping.shopping_mall_project.common.exception.EntityNotFoundException;
import com.parkjinhun.shopping.shopping_mall_project.domain.item.Item;
import com.parkjinhun.shopping.shopping_mall_project.domain.item.ItemInfo;
import com.parkjinhun.shopping.shopping_mall_project.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {
    private final ItemRepository itemRepository;

    @Override
    public Item getItemBy(String itemToken) {
        return itemRepository.findByItemToken(itemToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ItemInfo.ItemOptionGroupInfo> getItemOptionSeries(Item item) {
        var itemOptionGroupList = item.getItemOptionGroupList();
        return itemOptionGroupList.stream()
                .map(itemOptionGroup -> {
                    var itemOptionList = itemOptionGroup.getItemOptionList();
                    var itemOptionInfoList = itemOptionList.stream()
                            .map(ItemInfo.ItemOptionInfo::new)
                            .collect(Collectors.toList());

                    return new ItemInfo.ItemOptionGroupInfo(itemOptionGroup, itemOptionInfoList);
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findItemByKeyword(String keyword, Pageable pageable) {
        return itemRepository.findItemByItemNameContaining(keyword, pageable);
    }
}
