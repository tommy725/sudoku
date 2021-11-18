package sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SudokuField {
    private int value = 0;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructor with default value = 0.
     */
    public SudokuField() {
    }

    /**
     * Copy constructor.
     * @param sudokuField object of the same instance which should be copied
     */
    public SudokuField(SudokuField sudokuField) {
        this.value = sudokuField.value;
        this.pcs = new PropertyChangeSupport(sudokuField.pcs);
    }

    /**
     * Constructor with set concrete value.
     * @param value value which should be set
     */
    public SudokuField(int value) {
        this.value = value;
    }

    /**
     * Set sudoku field listener.
     * @param listener object which implements PropertyChangeListener
     */
    public void setListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener("value",listener);
    }

    /**
     * Getter of value parameter.
     * @return int value
     */
    public int getFieldValue() {
        return value;
    }

    /**
     * Setter of value parameter.
     * @param value which should be set
     */
    public void setFieldValue(int value) {
        int oldValue = this.value;
        this.value = value;
        if (oldValue != value) {
            pcs.firePropertyChange("value",oldValue,value);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value && Objects.equal(pcs, that.pcs);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, pcs);
    }
}
