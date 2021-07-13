package me.jakejmattson.bot.data

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.channel.TextChannel
import me.jakejmattson.discordkt.api.dsl.Data

data class Configuration(
    val ownerID: String = "448143136494190592",
    var prefix: String = "++",
    val guildConfigurations: MutableMap<Long, GuildConfiguration> = mutableMapOf()
) : Data("config/guild_config.json", killIfGenerated = false) {
    fun setup (
        guild: Guild,
        serverPrefix: String,
        staffReviewChannel: TextChannel,
        publicVotingChannel: TextChannel,
    ) {
        if (guildConfigurations[guild.id.value] != null) return
        val newGuildConfiguration = GuildConfiguration(
            guild.id.value,
            serverPrefix,
            staffReviewChannel.id,
            publicVotingChannel.id,
        )
        guildConfigurations[guild.id.value] = newGuildConfiguration
        save()
    }
    suspend fun addSuggestion(
        author: Member,
        randomLong: Long,
        suggestion: String,
    ) {
        val authorId = author.id.value
        val salt = authorId + randomLong
        guildConfigurations[author.getGuild().id.value]?.suggestions?.set(salt, suggestion)
    }
}

data class GuildConfiguration(
    val guildId: Long? = null,
    var serverPrefix: String = "sp!",
    var staffReviewChannel: Snowflake,
    var publicVotingChannel: Snowflake,
    val suggestions: MutableMap<Long, String> = mutableMapOf()
)
