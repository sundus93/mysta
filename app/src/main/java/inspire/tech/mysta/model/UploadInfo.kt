package inspire.tech.mysta.model;

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class UploadInfo {

    var name: String = ""
    var url: String = ""

    constructor() {}

    constructor(name: String, url: String) {
        this.name = name
        this.url = url
    }
}