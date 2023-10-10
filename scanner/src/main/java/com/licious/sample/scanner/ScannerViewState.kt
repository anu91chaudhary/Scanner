package com.licious.sample.scanner

/**
 *  Preferable possible states for a qr/bar code..
 */
sealed class ScannerViewState {
    object Success : ScannerViewState()
    object Error : ScannerViewState()
}