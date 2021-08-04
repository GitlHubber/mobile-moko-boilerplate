//
//  UIViewController+Extensions.swift
//  ios-app
//
//  Created by Andrew Kovalev on 02.05.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

extension UIViewController {
    func topPresentedViewController() -> UIViewController? {
        var topController = self.presentedViewController
        while let presentedViewController = topController?.presentedViewController {
            topController = presentedViewController
        }
        return topController
    }
}

// MARK: -  UIViewController livedata bind extension

extension UIViewController {
    @discardableResult
    func bind<V: UIView, T>(
        liveData: LiveData<T>,
        view: V,
        setter: @escaping (V, T?) -> Void
    ) -> Closeable {
        return liveData.bind(view: view) { (view, value) in
            guard let view = view as? V, let value = value as? T? else {
                return
            }
            setter(view, value)
        }
    }
    
    @discardableResult
    func bind<T>(
        liveData: LiveData<T>,
        setter: @escaping (T?) -> Void
    ) -> Closeable {
        return liveData.bind(view: view as Any) { (_, value) in
            guard let value = value as? T? else {
                return
            }
            setter(value)
        }
    }
}
