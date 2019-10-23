package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Sequence;

public interface SequenceMapper {
    Sequence getSequence(Sequence sequence);
    void updateSequence(Sequence sequence);
}
