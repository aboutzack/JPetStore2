package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {
    Sequence getSequence(Sequence sequence);
    void updateSequence(Sequence sequence);
}
