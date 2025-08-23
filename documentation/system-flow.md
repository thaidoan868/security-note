# System flow

Controller -> noteDto -> NoteServiceImpl.method(noteDto) -> noteMapper.toEntity(noteDto) -> note -> noteRepository.save(note)