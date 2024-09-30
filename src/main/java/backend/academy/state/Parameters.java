package backend.academy.state;

import backend.academy.constants.Category;
import backend.academy.constants.Difficult;
import backend.academy.ui.constants.Interface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Parameters {
    private Category category;

    private Difficult difficult;

    public String toString() {
        String settings = "";
        if (this.difficult != null) {
            settings +=  Interface.DIFFICULT_TEXT + " " + Difficult.convertToString(this.difficult()) + "\n";
        }
        if (this.category() != null) {
            settings +=  Interface.CATEGORY_TEXT + " " + Category.convertToString(this.category()) + "\n";
        }
        return settings;
    }

}
