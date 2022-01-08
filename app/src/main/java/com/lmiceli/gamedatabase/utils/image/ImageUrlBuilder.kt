package com.lmiceli.gamedatabase.utils.image

object ImageUrlBuilder {
    /*
    *
    * https://images.igdb.com/igdb/image/upload/t_screenshot_med_2x/dfgkfivjrhcksyymh9vw.jpg
    *
    *
    * cover_small	90 x 128	Fit
      screenshot_med	569 x 320	Lfill, Center gravity
      cover_big	264 x 374	Fit
      logo_med	284 x 160	Fit
      screenshot_big	889 x 500	Lfill, Center gravity
      screenshot_huge	1280 x 720	Lfill, Center gravity
      thumb	90 x 90	Thumb, Center gravity
      micro	35 x 35	Thumb, Center gravity
      720p	1280 x 720	Fit, Center gravity
      1080p	1920 x 1080	Fit, Center gravity
    *
    * */

    enum class SIZE(val urlSection: String) {
        THUMB("thumb"),
        SMALL("cover_big"),
        BIG("1080p"),
    }


    private fun getImage(thumbUrl: String, size: SIZE): String {
        val path = thumbUrl.replace(SIZE.THUMB.urlSection, size.urlSection, true)
        return "https:$path"
    }

    fun getSmallImage(id: String?): String? {
        return id?.let {
            getImage(it, SIZE.SMALL)
        }
    }

    fun getBigImage(id: String?): String? {
        return id?.let {
            getImage(it, SIZE.BIG)
        }
    }

}
