//
//  Entity.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/19/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import Foundation


class Food : NSObject {    //all NSObjects in Kinvey implicitly implement KCSPersistable
    var _id: String?
    var count: String?
    var roma: String?
    var unit:String?
    var usFoods:String?
    var walmartHyvee:String?
    var metadata: KCSMetadata? //Kinvey metadata, optional
    
    
    
    
    override func hostToKinveyPropertyMapping() -> [NSObject : AnyObject]! {
        return [
            "_id" : "_id",
            "count" : "count",
            "roma" : "roma",
            "unit" : "unit",
            "usFoods":"usFoods",
            "walmartHyvee":"walmartHyvee",
            "metadata" : KCSEntityKeyMetadata //optional _metadata field
        ]
    }
    
    
    
}
