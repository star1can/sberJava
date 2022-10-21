package types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class Owner {
    private final long ownerId;
    private final String name;
    private final String lastName;
    private final int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return ownerId == owner.ownerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId);
    }
}
