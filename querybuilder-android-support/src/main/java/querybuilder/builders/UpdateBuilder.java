package querybuilder.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import querybuilder.helpers.ColumnHelper;
import querybuilder.interfaces.QueryBuilder;
import querybuilder.query.Comparisons;
import querybuilder.query.Condition;
import querybuilder.query.update.UpdateColumn;

/**
 * Created by augustoccesar on 6/17/16.
 */
public class UpdateBuilder implements QueryBuilder {
    private String tableName;
    private List<UpdateColumn> updateColumns;
    private Condition conditionBase;
    private boolean withValues = false;

    public UpdateBuilder table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UpdateBuilder where(Condition condition) {
        this.conditionBase = condition;
        return this;
    }

    public UpdateBuilder columns(UpdateColumn... updateColumns) {
        if (this.updateColumns == null)
            this.updateColumns = new ArrayList<>();
        Collections.addAll(this.updateColumns, updateColumns);
        return this;
    }

    public UpdateBuilder withValues() {
        this.withValues = true;
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(this.tableName);
        stringBuilder.append(" SET ");
        for (int i = 0; i < this.updateColumns.size(); i++) {
            UpdateColumn updateColumn = this.updateColumns.get(i);
            stringBuilder.append(updateColumn.getField()).append(" = ");
            if (withValues) {
                if (updateColumn.getValue() != null) {
                    stringBuilder.append(ColumnHelper.checkValueForInsert(updateColumn.getValue()));
                } else {
                    // TODO Exception handlers
                    return null;
                }
            } else {
                stringBuilder.append(Comparisons.VARIABLE.getValue());
            }

            if (i < this.updateColumns.size() - 1)
                stringBuilder.append(", ");
        }

        if (this.conditionBase != null) {
            stringBuilder.append(" WHERE ");
            ColumnHelper.runNestedConditions(stringBuilder, conditionBase);
        }

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
