package lotto.domain;

import static common.ErrorCode.BONUS_NUMBER_ALREADY_REGISTERED;
import static common.ErrorCode.WINNING_NUMBERS_CONTAIN_BONUS_NUMBER;
import static common.ErrorCode.WINNING_NUMBERS_DUPLICATED;
import static common.ErrorCode.WINNING_NUMBERS_INVALID_SIZE;
import static java.util.stream.Collectors.toList;

import common.exception.InvalidArgumentException;
import common.exception.InvalidStatementException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class WinningNumbers {

    private static final int VALID_SIZE = 6;
    private final List<WinningNumber> numbers;
    private WinningNumber bonusNumber;

    public WinningNumbers(List<Integer> numbers) {
        validateNumbers(numbers);
        this.numbers = convertWinningNumbers(numbers);
    }

    public void addBonus(WinningNumber bonusNumber) {
        validateBonusNumber(bonusNumber);
        this.bonusNumber = bonusNumber;
    }

    private List<WinningNumber> convertWinningNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(WinningNumber::new)
                .collect(toList());
    }

    private void validateNumbers(List<Integer> numbers) {
        if (!isValidSize(numbers)) {
            throw new InvalidArgumentException(WINNING_NUMBERS_INVALID_SIZE);
        }

        if (!isUniqueNumbers(numbers)) {
            throw new InvalidArgumentException(WINNING_NUMBERS_DUPLICATED);
        }
    }

    private boolean isValidSize(List<Integer> numbers) {
        return numbers.size() == VALID_SIZE;
    }

    private boolean isUniqueNumbers(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        return uniqueNumbers.size() == numbers.size();
    }

    private void validateBonusNumber(WinningNumber bonusNumber) {
        if (!isBonusNumberNull()) {
            throw new InvalidStatementException(BONUS_NUMBER_ALREADY_REGISTERED);
        }

        if (!isUniqueBonusNumber(bonusNumber)) {
            throw new InvalidArgumentException(WINNING_NUMBERS_CONTAIN_BONUS_NUMBER);
        }
    }

    private boolean isBonusNumberNull() {
        return Objects.isNull(this.bonusNumber);
    }

    private boolean isUniqueBonusNumber(WinningNumber bonusNumber) {
        return !numbers.contains(bonusNumber);
    }
}
