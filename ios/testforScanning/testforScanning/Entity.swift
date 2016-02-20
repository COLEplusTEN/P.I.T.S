//
//  Entity.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/19/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import Foundation


class Food : NSObject {    //all NSObjects in Kinvey implicitly implement KCSPersistable
    var entityId: String? //Kinvey entity _id
    var count: String?
    
    var roma: String?
    var unit:String?
    var usFoods:String?
    var walmartHyvee:String?
    var metadata: KCSMetadata? //Kinvey metadata, optional
    
    
    
    
    override func hostToKinveyPropertyMapping() -> [NSObject : AnyObject]! {
        return [
            "entityId" : KCSEntityKeyId, //the required _id field
            "count" : "count",
            "roma" : "roam",
            "unit" : "unit",
            "usFoods":"usFoods",
            "walmartHyvee":"walmartHyvee",
            "metadata" : KCSEntityKeyMetadata //optional _metadata field
        ]
    }
    
    
    
}
