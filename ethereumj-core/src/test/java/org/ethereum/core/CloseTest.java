/*
 * Copyright (c) [2016] [ <ether.camp> ]
 * This file is part of the ethereumJ library.
 *
 * The ethereumJ library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ethereumJ library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ethereumJ library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.ethereum.core;

import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.publish.event.BlockAddedEvent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.ethereum.publish.Subscription.to;

/**
 * Created by Anton Nashatyrev on 24.06.2016.
 */
public class CloseTest {

    @Ignore
    @Test
    public void relaunchTest() throws InterruptedException {

        while (true) {
            Ethereum ethereum = EthereumFactory.createEthereum();
            Block bestBlock = ethereum.getBlockchain().getBestBlock();
            Assert.assertNotNull(bestBlock);
            final CountDownLatch latch = new CountDownLatch(1);
            AtomicInteger counter = new AtomicInteger();
            ethereum.subscribe(to(BlockAddedEvent.class, blockSummary -> {
                if (counter.addAndGet(1) > 1100)  {
                    latch.countDown();
                }
            }));

            System.out.println("### Waiting for some blocks to be imported...");
            latch.await();
            System.out.println("### Closing Ethereum instance");
            ethereum.close();
            Thread.sleep(5000);
            System.out.println("### Closed.");
        }
    }
}
