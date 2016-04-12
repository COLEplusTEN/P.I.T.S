//
//  UIOutlinedLabel.swift
//  testforScanning
//
//  Created by Coleten McGuire on 4/12/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import UIKit

import UIKit

class UIOutlinedLabel: UILabel {
    
    var outlineWidth: CGFloat = 1
    var outlineColor: UIColor = UIColor.whiteColor()
    
    override func drawTextInRect(rect: CGRect) {
        
        let strokeTextAttributes = [
            NSStrokeColorAttributeName : outlineColor,
            NSStrokeWidthAttributeName : -1 * outlineWidth,
        ]
        
        self.attributedText = NSAttributedString(string: self.text ?? "", attributes: strokeTextAttributes)
        super.drawTextInRect(rect)
    }
}
