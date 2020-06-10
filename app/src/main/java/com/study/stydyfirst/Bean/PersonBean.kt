package com.study.stydyfirst.Bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 实现了parcelable接口的实体类，可用于跨进程传输
 */
class PersonBean() : Parcelable {
    var name: String? = null;

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "PersonBean(name=$name)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonBean

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name?.hashCode() ?: 0
    }

    companion object CREATOR : Parcelable.Creator<PersonBean> {
        override fun createFromParcel(parcel: Parcel): PersonBean {
            return PersonBean(parcel)
        }

        override fun newArray(size: Int): Array<PersonBean?> {
            return arrayOfNulls(size)
        }
    }

}