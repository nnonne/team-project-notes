ALTER TABLE notes
ADD user_id bigserial REFERENCES users(id);
