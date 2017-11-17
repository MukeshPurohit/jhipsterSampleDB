ALTER TABLE operation ADD CONSTRAINT fk_operation_bank_account_id FOREIGN KEY (bank_account_id) REFERENCES bank_account(id);
ALTER TABLE operation_label ADD CONSTRAINT fk_operation_label_operations_id FOREIGN KEY (operations_id) REFERENCES operation(id);
ALTER TABLE operation_label ADD CONSTRAINT fk_operation_label_labels_id FOREIGN KEY (labels_id) REFERENCES label(id);