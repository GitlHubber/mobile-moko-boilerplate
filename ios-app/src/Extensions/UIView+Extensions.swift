//
//  UIView+Extensions.swift
//  ios-app
//
//  Created by Andrey Dorofeev on 17.06.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import MultiPlatformLibrary
import UIKit

extension UIView {
    @discardableResult
    func bind<T>(
        liveData: LiveData<T>,
        setter: @escaping (UIView, T?) -> Void
    ) -> Closeable {
        return liveData.bind(view: self) { (view, value) in
            guard let view = view as? UIView, let value = value as? T? else {
                return
            }
            setter(view, value)
        }
    }
}
