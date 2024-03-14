package org.spring.listforparty.services;

import lombok.RequiredArgsConstructor;
import org.spring.listforparty.dto.OfferDto;
import org.spring.listforparty.models.Offer;
import org.spring.listforparty.repo.OfferRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public void saveNewOffer(OfferDto offerDto) {
        Offer offer = new Offer();
        offer.setText(offerDto.getText());
        offerRepository.save(offer);
    }
}
