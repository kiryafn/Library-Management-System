package com.library.library_management.domain.services;

import com.library.library_management.data.entities.Book;
import com.library.library_management.data.entities.Copy;
import com.library.library_management.data.entities.CopyStatus;
import com.library.library_management.data.repository.CopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopyService {

    public CopyRepository copyRepository;

    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public Copy addCopy(Copy copy) {
        validateCopy(copy);
        return copyRepository.save(copy);
    }

    // Обновление существующей копии
    public Copy updateCopy(Copy copy) {
        validateCopy(copy);
        return copyRepository.save(copy);
    }

    // Удаление копии по ID
    public void deleteCopy(Long copyId) {
        Copy copy = copyRepository.findById(copyId).orElseThrow(() ->
                new IllegalArgumentException("Копия с ID " + copyId + " не найдена."));

        // Проверка: нельзя удалить копию, если она не в статусе "Available"
        if (!CopyStatus.Available.getName().equals(copy.getStatus())) {
            throw new IllegalStateException("Нельзя удалить копию, которая не доступна.");
        }

        copyRepository.delete(copy);
    }

    public Copy getCopyById(Long copyId) {
        return copyRepository.findById(copyId)
                .orElseThrow(() -> new IllegalArgumentException("Копия с ID " + copyId + " не найдена."));
    }

    // Получение всех копий конкретной книги
    public List<Copy> getCopiesByBook(Book book) {
        return copyRepository.findByBook(book);
    }

    // Получение всех копий по статусу
    public List<Copy> getCopiesByStatus(String status) {
        return copyRepository.findByStatus(status);
    }

    // Валидация копии
    private void validateCopy(Copy copy) {
        if (copy.getBook() == null || copy.getBook().getId() == null) {
            throw new IllegalArgumentException("The copy should be attached to the book.");
        }

        if (copy.getCopyNumber() == null || copy.getCopyNumber() <= 0) {
            throw new IllegalArgumentException("У копии должен быть положительный номер.");
        }

        if (copy.getStatus() == null || copy.getStatus().isEmpty()) {
            throw new IllegalArgumentException("У копии должен быть установлен статус.");
        }

        // Проверяем, существует ли уже такая копия с таким номером для данной книги
        Optional<Copy> existingCopy = copyRepository.findByBookAndCopyNumber(copy.getBook(), copy.getCopyNumber());
        if (existingCopy.isPresent() && !existingCopy.get().getId().equals(copy.getId())) {
            throw new IllegalStateException("Копия с номером " + copy.getCopyNumber() + " уже существует для этой книги.");
        }
    }
}
