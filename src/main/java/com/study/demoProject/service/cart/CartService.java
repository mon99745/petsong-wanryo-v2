package com.study.demoProject.service.cart;

import com.study.demoProject.domain.cart.CartEntity;
import com.study.demoProject.domain.cart.CartLine;
import com.study.demoProject.domain.cart.CartRepository;
import com.study.demoProject.domain.item.ItemRepository;
import com.study.demoProject.model.dao.cart.CartDao;
import com.study.demoProject.model.dto.cart.CartLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartDao cartDao;

    public Long createCart(Long code) {
        return cartRepository.save(new CartEntity(code))
                .getCartId();
    }

    public void addItemToCart(Long code, AddToCartRequestForm addToCartRequestForm) {
        CartEntity cartEntity = cartRepository.findFirstByCode(code);

        CartLine newCartLine = new CartLine(cartEntity.getCartId(),
                addToCartRequestForm.getItemId(),
                addToCartRequestForm.getOrderCount());

        int targetStockQuantity = itemRepository.findById(addToCartRequestForm.getItemId())
                .get()
                .getStockQuantity();

        cartEntity.addItemToCart(targetStockQuantity, newCartLine);
    }

    public List<CartLineDto> getCartInCartPage(Long code) {
        return cartDao.getCartLineListInCartPage(code);
    }

    public void modifyOrderCount(Long code, ModifyOrderCountRequestForm modifyOrderCountRequestForm) {
        // 엔티티 조회
        CartEntity cartEntity = cartRepository.findFirstByCode(code);
        int targetStockQuantity = itemRepository.findById(modifyOrderCountRequestForm.getItemId())
                .get()
                .getStockQuantity();

        CartLine newCartLine = new CartLine(cartEntity.getCartId(), modifyOrderCountRequestForm.getItemId(), modifyOrderCountRequestForm.getOrderCount());
        cartEntity.modifyOrderCount(targetStockQuantity, newCartLine);
    }

    //  특정 상품들만 주문하는 경우가 존재하므로, 장바구니를 그냥 비우는게 아닌, id를 기준으로 비워야함
    public void removeCartLines(Long code, List<Long> itemIds) {
        CartEntity cartEntity = cartRepository.findFirstByCode(code);

        itemIds.stream()
                .forEach(itemId -> cartEntity.removeCartLine(itemId));
    }

    public void removeCartLine(Long code, Long itemId) {
        CartEntity cartEntity = cartRepository.findFirstByCode(code);
        cartEntity.removeCartLine(itemId);
    }
}
