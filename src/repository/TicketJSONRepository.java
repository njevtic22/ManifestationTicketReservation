package repository;

import model.Ticket;
import serializer.serializer.FileSerializer;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TicketJSONRepository implements Repository<Ticket, Long> {
    // Filled in file deserializer
    private final Map<Long, Ticket> data = new TreeMap<>();
    private final FileSerializer<Ticket, Long> fileSerializer;

    public TicketJSONRepository(FileSerializer<Ticket, Long> fileSerializer) {
        this.fileSerializer = fileSerializer;
    }

    @Override
    public void save(Ticket entity) {
        data.put(entity.getId(), entity);
        fileSerializer.save(data);
    }

    @Override
    public Collection<Ticket> findAll() {
        return data.values();
    }

    @Override
    public Collection<Ticket> findAllByArchivedFalse() {
        return findAll()
                .stream()
                .filter(ticket -> !ticket.isArchived())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<Ticket> findByIdAndArchivedFalse(Long id) {
        Optional<Ticket> ticketOptional = findById(id);

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            if (ticket.isArchived())
                return Optional.empty();
        }

        return ticketOptional;
    }

    @Override
    public void delete(Ticket entity) {
        data.remove(entity.getId());
        fileSerializer.save(data);
    }

    @Override
    public long count() {
        return data.size();
    }

    Map<Long, Ticket> getData() {
        return data;
    }
}
