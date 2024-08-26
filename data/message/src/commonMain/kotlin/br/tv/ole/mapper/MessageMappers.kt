package br.tv.ole.mapper

import br.tv.ole.model.Message

fun Message.toData(): br.tv.ole.db.Message {
    return br.tv.ole.db.Message(
        id = id,
        title = title,
        content = content,
        sent = sent,
        read = read,
    )
}

fun br.tv.ole.db.Message.asModel(): Message {
    return Message(
        id = id,
        title = title,
        content = content,
        sent = sent,
        read = read,
    )
}
