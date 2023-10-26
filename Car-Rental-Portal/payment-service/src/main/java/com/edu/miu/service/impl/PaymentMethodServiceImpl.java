package com.edu.miu.service.impl;

import com.edu.miu.domain.MasterCardPaymentMethod;
import com.edu.miu.domain.PaymentMethod;
import com.edu.miu.domain.VisaPaymentMethod;
import com.edu.miu.dto.BusinessException;
import com.edu.miu.dto.PaymentMethodDTO;
import com.edu.miu.service.kafka.KafkaProducerService;
import com.edu.miu.enums.CardType;
import com.edu.miu.repository.PaymentMethodRepository;
import com.edu.miu.service.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

    @Override
    public PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethod) throws BusinessException {
        Optional<PaymentMethod> existingPaymentMethod = Optional.ofNullable(paymentMethodRepository.findByCardNumber(paymentMethod.getCardNumber()));
        if (existingPaymentMethod.isPresent()) {
            logger.error("Attempt to create a PaymentMethod with an already existing card number: {}", paymentMethod.getCardNumber());
            throw new IllegalArgumentException("A PaymentMethod with this card number already exists.");
        }

        CardType cardType = paymentMethod.getCardType();

        PaymentMethod savedPaymentMethod;

        switch (cardType) {
            case VISA -> savedPaymentMethod = modelMapper.map(paymentMethod, VisaPaymentMethod.class);
            case MASTER_CARD -> savedPaymentMethod = modelMapper.map(paymentMethod, MasterCardPaymentMethod.class);
            default -> throw new BusinessException(String.format("Unknown card type %s", cardType));
        }

        paymentMethodRepository.save(savedPaymentMethod);
        logger.info("Successfully created new payment method with ID: {}", savedPaymentMethod.getMethodId());

        kafkaProducerService.sendMessage("payment-topic", "New payment method added with ID: " + savedPaymentMethod.getMethodId());
        String notificationMessage = String.format("User with ID %s added a new payment method with ID: %s", savedPaymentMethod.getUserId(), savedPaymentMethod.getMethodId());
        kafkaProducerService.sendMessage("user-notification-topic", notificationMessage);

        return PaymentMethodDTO.toDto(savedPaymentMethod);
    }

    @Override
    public PaymentMethodDTO updatePaymentMethod(Integer methodId, PaymentMethodDTO dto) throws BusinessException {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(methodId).get();
        if (paymentMethod == null) {
            logger.error("Attempt to update a PaymentMethod that doesn't exist with ID: {}", methodId);
            throw new IllegalArgumentException("Cannot update a PaymentMethod that doesn't exist.");
        }

        if (dto.getCardNumber() != null && !dto.getCardNumber().equals(paymentMethod.getCardNumber())) {
            paymentMethod.setCardNumber(dto.getCardNumber());
        }

        if (dto.getExpiryDate() != null && !dto.getExpiryDate().equals(paymentMethod.getExpiryDate())) {
            paymentMethod.setExpiryDate(dto.getExpiryDate());
        }

        if (dto.getCvv() != null && !dto.getCvv().equals(paymentMethod.getCvv())) {
            paymentMethod.setCvv(dto.getCvv());
        }

        if (dto.getCardType() != null && !dto.getCardType().equals(paymentMethod.getCardType())) {
            paymentMethod.setCardType(dto.getCardType());
        }

        if (dto.getApprovalAmount() > 0 && dto.getApprovalAmount() != paymentMethod.getApprovalAmount()) {
            paymentMethod.setApprovalAmount(dto.getApprovalAmount());
        }

        if (dto.getUsedAmount() > 0 && dto.getUsedAmount() != paymentMethod.getUsedAmount()) {
            paymentMethod.setUsedAmount(dto.getUsedAmount());
        }

        if (dto.getTotalUsed() > 0 && dto.getTotalUsed() != paymentMethod.getTotalUsed()) {
            paymentMethod.setTotalUsed(dto.getTotalUsed());
        }

        if (dto.getBalance() > 0 && dto.getBalance() != paymentMethod.getBalance()) {
            paymentMethod.setBalance(dto.getBalance());
        }

        paymentMethodRepository.save(paymentMethod);
        logger.info("Successfully updated payment method with ID: {}", paymentMethod.getMethodId());

        return PaymentMethodDTO.toDto(paymentMethod);
    }



    @Override
    public List<PaymentMethodDTO> findByUserId(Integer userId) {
        return paymentMethodRepository.findByUserId(userId).stream()
                .map(PaymentMethodDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentMethodDTO> findById(Integer id) {
        return Optional.ofNullable(PaymentMethodDTO.toDto(paymentMethodRepository.findById(id).get()));
    }

    @Override
    public void deleteById(Integer id) {
        if (!paymentMethodRepository.existsById(id)) {
            logger.warn("Attempt to delete non-existing PaymentMethod with ID: {}", id);
            return;
        }
        paymentMethodRepository.deleteById(id);
        logger.info("Deleted PaymentMethod with ID: {}", id);
    }

    @Override
    public Optional<PaymentMethodDTO> findByCardNumber(String cardNumber) {
        return Optional.ofNullable(PaymentMethodDTO.toDto(paymentMethodRepository.findByCardNumber(cardNumber)));
    }

    @Override
    public List<PaymentMethodDTO> findByCardType(CardType cardType) {
        return paymentMethodRepository.findByCardType(cardType).stream()
                .map(PaymentMethodDTO::toDto)
                .collect(Collectors.toList());
    }
}
