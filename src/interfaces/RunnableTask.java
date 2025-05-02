/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 * Runnable task interface that implements Java's standard Runnable interface
 * Used for creating multi-threaded tasks
 * 
 * @author soyeon,yiming
 */
public interface RunnableTask extends Runnable {
    //
    String getTaskName();
    String getStatus();
    void pauseTask();
    void resumeTask();
}