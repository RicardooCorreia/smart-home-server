package database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import model.EntityModel;

public abstract class AbstractRepository<T extends EntityModel> {
    private final DatabaseReference databaseReference;

    AbstractRepository(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void save(T entity) {
        databaseReference.child(entity.getKey()).setValueAsync(entity);
    }

    public void addChildEventListener(ChildEventListener childEventListener) {
        databaseReference.addChildEventListener(childEventListener);
    }

    public DatabaseReference getReference() {
        return this.databaseReference;
    }
}
