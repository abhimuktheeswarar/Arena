package com.abhi.spectacle.data.poko

import com.squareup.moshi.Json

data class ImgurEntities(
        val data: List<ImgurData>,
        val success: Boolean,
        val status: Int
) {
    data class ImgurData(
            val id: String,
            val title: String,
            val description: Any?,
            val datetime: Int,
            val cover: String,
            @Json(name = "cover_width")
            val coverWidth: Int,
            @Json(name = "cover_height")
            val coverHeight: Int,
            @Json(name = "account_url")
            val accountUrl: String,
            @Json(name = "account_id")
            val accountId: Int,
            val privacy: String,
            val layout: String,
            val views: Int,
            val link: String,
            val ups: Int,
            val downs: Int,
            val points: Int,
            val score: Int,
            @Json(name = "is_album")
            val isAlbum: Boolean,
            val vote: Any?,
            val favorite: Boolean,
            val nsfw: Boolean,
            val section: String,
            @Json(name = "comment_count")
            val commentCount: Int,
            @Json(name = "favorite_count")
            val favoriteCount: Int,
            val topic: String,
            @Json(name = "topic_id")
            val topicId: Int,
            @Json(name = "images_count")
            val imagesCount: Int,
            @Json(name = "in_gallery")
            val inGallery: Boolean,
            @Json(name = "is_ad")
            val isAd: Boolean,
            val tags: List<Tag>,
            @Json(name = "ad_type")
            val adType: Int,
            @Json(name = "ad_url")
            val adUrl: String,
            @Json(name = "in_most_viral")
            val inMostViral: Boolean,
            @Json(name = "include_album_ads")
            val includeAlbumAds: Boolean,
            val images: List<Image>?,
            val type: String,
            val animated: Boolean,
            val width: Int,
            val height: Int,
            val size: Int,
            val bandwidth: Long,
            @Json(name = "has_sound")
            val hasSound: Boolean,
            val mp4: String,
            val gifv: String,
            @Json(name = "mp4_size")
            val mp4Size: Int,
            val looping: Boolean,
            val processing: Processing
    ) {
        data class Tag(
                val name: String,
                @Json(name = "display_name")
                val displayName: String,
                val followers: Int,
                @Json(name = "total_items")
                val totalItems: Int,
                val following: Boolean,
                @Json(name = "background_hash")
                val backgroundHash: String,
                @Json(name = "thumbnail_hash")
                val thumbnailHash: Any?,
                val accent: String,
                @Json(name = "background_is_animated")
                val backgroundIsAnimated: Boolean,
                @Json(name = "thumbnail_is_animated")
                val thumbnailIsAnimated: Boolean,
                @Json(name = "is_promoted")
                val isPromoted: Boolean,
                val description: String,
                @Json(name = "logo_hash")
                val logoHash: Any?,
                @Json(name = "logo_destination_url")
                val logoDestinationUrl: Any?,
                @Json(name = "description_annotations")
                val descriptionAnnotations: DescriptionAnnotations
        ) {
            data class DescriptionAnnotations(val id: Any
            )
        }

        data class Image(
                val id: String,
                val title: Any?,
                val description: String,
                val datetime: Int,
                val type: String,
                val animated: Boolean,
                val width: Int,
                val height: Int,
                val size: Int,
                val views: Int,
                val bandwidth: Long,
                val vote: Any?,
                val favorite: Boolean,
                val nsfw: Any?,
                val section: Any?,
                @Json(name = "account_url")
                val accountUrl: Any?,
                @Json(name = "account_id")
                val accountId: Any?,
                @Json(name = "is_ad")
                val isAd: Boolean,
                @Json(name = "in_most_viral")
                val inMostViral: Boolean,
                @Json(name = "has_sound")
                val hasSound: Boolean,
                val tags: List<Any>,
                @Json(name = "ad_type")
                val adType: Int,
                @Json(name = "ad_url")
                val adUrl: String,
                @Json(name = "in_gallery")
                val inGallery: Boolean,
                val link: String,
                @Json(name = "mp4_size")
                val mp4Size: Int,
                val mp4: String,
                val gifv: String,
                val processing: Processing,
                @Json(name = "comment_count")
                val commentCount: Any?,
                @Json(name = "favorite_count")
                val favoriteCount: Any?,
                val ups: Any?,
                val downs: Any?,
                val points: Any?,
                val score: Any?
        ) {
            data class Processing(
                    val status: String
            )
        }

        data class Processing(
                val status: String
        )
    }
}